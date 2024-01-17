from datetime import datetime
from datetime import timedelta
import mysql.connector
import requests
import re
import os 
import shutil


REMOTE = 'woori-fisa2.cfnz7hfzq9bn.ap-northeast-2.rds.amazonaws.com'
(HOST, USER, PASSWORD, DATABASE) = (REMOTE, 'admin', 'woorifisa2!', 'I_HATE_JAVA')

def connect_mysql() :
    con = mysql.connector.connect(
        host = HOST,
        user = USER,
        password = PASSWORD,
        database = DATABASE
    )
    return con

def webCrawling_menu(date_datetime):
    start_of_week = date_datetime - timedelta(days = date_datetime.weekday())
    formatted_start_of_week = start_of_week.strftime("%Y%m%d")
    formatted_end_of_week = (start_of_week + timedelta(days=4)).strftime("%Y%m%d")

    HEADERS = {
        "KEY": "836c168790264393ab355e19a0cdef10",
    }

    PARAMETERS = {
        "Type": "json",
        "ATPT_OFCDC_SC_CODE": "B10",
        "SD_SCHUL_CODE": 7010132,
        "MMEAL_SC_CODE": 2,
        "MLSV_FROM_YMD": formatted_start_of_week,
        "MLSV_TO_YMD": formatted_end_of_week
    }

    URL = r"https://open.neis.go.kr/hub/mealServiceDietInfo"

    response = requests.get(URL,
                            headers = HEADERS,
                            params = PARAMETERS
                            )
    
    data = response.json()
    if 'RESULT' in data and 'CODE' in data['RESULT'] and data['RESULT']['CODE'] == 'INFO-200':
        print("데이터 없음 : 실행 종료...")
    else:
        meal_data = data['mealServiceDietInfo'][1]['row']
        insert_menu(meal_data)

def insert_menu(meal_data) :
    SQL_INSERT_DATA = """INSERT INTO TABLE_MENU
                        (BASEDATE, SCHOOL, ITEM_NUMBER, ITEM_NAME)
                        VALUES
                        ('%s', '%s', %s, '%s')
                        """

    with connect_mysql() as con:
        with con.cursor() as cur:
            try :
                SQL_DELETE = "DELETE FROM TABLE_MENU"
                cur.execute(SQL_DELETE)
                con.commit()
            except Exception as e:
                print (str(e))

    with connect_mysql() as con:
        with con.cursor() as cur:
            for meal_info in meal_data:
                yyyymmdd = meal_info['MLSV_YMD']
                schoolnm = meal_info['SCHUL_NM']
                
                yyyymmdd = datetime.strptime(yyyymmdd, "%Y%m%d")
                ddish_nm = meal_info.get('DDISH_NM', '')
                
                # 정규식을 사용하여 괄호와 <br/> 태그 제거
                ddish_nm_cleaned = re.sub(r'\([^)]*\)', '', ddish_nm)
                ddish_nm_cleaned = ddish_nm_cleaned.replace('<br/>', '')
                ddish_nm_cleaned = ddish_nm_cleaned.replace('</br>', '')
                ddish_nm_listed = ddish_nm_cleaned.split()

                
                for idx, menu in enumerate(ddish_nm_listed) :                            
                    try :
                        SQL_INSERT = SQL_INSERT_DATA %(yyyymmdd,
                                                        schoolnm,
                                                        str(idx + 1),
                                                        menu
                                                        )
                        cur.execute(SQL_INSERT)
                        con.commit()

                    except Exception as e:
                        print (str(e))
                    


if __name__ == '__main__' :
    # date_string = datetime.today().strftime('%Y%m%d')
    date_string = "20240112"
    date_datetime = datetime.strptime(date_string, '%Y%m%d')

    webCrawling_menu(date_datetime)