from datetime import datetime
from datetime import timedelta
import requests
import re
import os 
import shutil

start_of_week = datetime.today() - timedelta(days=datetime.today().weekday())
formatted_start_of_week = start_of_week.strftime("%Y%m%d")
formatted_end_of_week = (start_of_week + timedelta(days=4)).strftime("%Y%m%d")

PATH_WINDOWS = os.getcwd().split('\\')

PATH_PYTION = r''
for ii in range(len(PATH_WINDOWS)) :
    PATH_PYTION += str(PATH_WINDOWS[ii] + r'/') 

PATH_PYTION += 'data/'

HEADERS = {
    "KEY": "NULL",
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

# 응답 출력
data = response.json()

meal_data = data['mealServiceDietInfo'][1]['row']

formatted_datetime = datetime.now().strftime("%Y%m%d%H%M%S")
if os.path.exists(PATH_PYTION + r'output.txt') :
    shutil.move(PATH_PYTION + r'output.txt', PATH_PYTION + r'output' + formatted_datetime + r'.txt')


with open(PATH_PYTION + 'output.txt', 'w', encoding='utf-8') as file:
    for meal_info in meal_data:
        ddish_nm = meal_info.get('DDISH_NM', '')
        mlsv_from_ymd = meal_info.get('MLSV_FROM_YMD', '')
        
        # 정규식을 사용하여 괄호와 <br/> 태그 제거
        ddish_nm_cleaned = re.sub(r'\([^)]*\)', '', ddish_nm)
        ddish_nm_cleaned = ddish_nm_cleaned.replace('<br/>', '')
        ddish_nm_cleaned = ddish_nm_cleaned.replace('</br>', '')
        ddish_nm_listed = ddish_nm_cleaned.split()
        ddish_nm_message = ""
        for idx in range(len(ddish_nm_listed)) :
            # print (str(idx) + ". " + ddish_nm_listed[idx] + "\n")
            ddish_nm_message += str(idx) + ". " + ddish_nm_listed[idx] + "\n"
        
          
        # 파일에 날짜 및 메뉴 정보 기록
        # print (f'[{mlsv_from_ymd}][광성고등학교]\n{ddish_nm_message}\n')
        file.write(f'[{mlsv_from_ymd}][광성고등학교]\n{ddish_nm_message}\n')
        # print (1)

print("텍스트 파일로 저장하였습니다.")