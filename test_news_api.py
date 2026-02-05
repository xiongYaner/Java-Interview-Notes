import requests
from bs4 import BeautifulSoup

def test_bing_news_api():
    url = "https://www.bing.com/news/rss"
    response = requests.get(url)
    print("响应状态码：", response.status_code)
    print("响应内容：")
    print(response.text)
    
    soup = BeautifulSoup(response.text, 'xml')
    print("\n解析后的内容：")
    print(soup.prettify())

if __name__ == "__main__":
    test_bing_news_api()