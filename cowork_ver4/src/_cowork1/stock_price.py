import requests
from bs4 import BeautifulSoup
import re

def crawl(URL):
    response = requests.get(URL)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    return soup

def extract_stock_code(company_name):
    # 정규 표현식을 사용하여 숫자만 추출
    stock_code = re.sub("[^0-9]", "", company_name)
    return stock_code

def get_stock_price(URL_final):
    soup_final = crawl(URL_final)
    today_price_element = soup_final.select_one(".today .no_today")

    if today_price_element:
        today_price = today_price_element.find("span", class_="blind").get_text(strip=True)
        return today_price
    else:
        return "주식 가격 정보를 찾을 수 없습니다."

def main():
    # word = input('원하는 종목을 입력하시오.')
    word = "삼성전자"
    URL = 'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=' + word
    soup = crawl(URL)
    company_name_element = soup.select_one(".t_nm")

    if company_name_element:
        company_name_text = company_name_element.get_text()
        # print(f"입력한 기업명: {company_name_text}")
        # confirmation = input("이 기업명이 맞습니까? (Y/N): ").upper()
        confirmation = 'Y'

        if confirmation == 'Y':
            # 숫자만 추출
            stock_code = extract_stock_code(company_name_text)
            # print(f"종목 코드: {stock_code}")
            URL_final = "https://finance.naver.com/item/main.naver?code=" + stock_code
            today_price = get_stock_price(URL_final)
            # return "{word}의 현재가는 {today_price}원입니다."
            print( f"Samsung's price is {today_price}"  )
        else:
            # print("기업명이 일치하지 않습니다.")
            pass
    else:
        # print("종목 정보를 찾을 수 없습니다.")
        pass

if __name__ == "__main__":
    main()
