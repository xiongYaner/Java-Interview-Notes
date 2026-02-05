import requests
from bs4 import BeautifulSoup

def get_news_summary():
    # 使用Bing News API获取全球新闻
    url = "https://www.bing.com/news/rss"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'xml')
    
    # 提取新闻标题和链接
    news_items = soup.find_all('item')
    news_summary = []
    
    for item in news_items[:10]:
        title = item.title.text
        link = item.link.text
        pub_date = item.pubDate.text
        
        news_summary.append({
            "title": title,
            "link": link,
            "pub_date": pub_date
        })
    
    return news_summary

def format_news_summary(news_summary):
    # 格式化新闻总结
    formatted_summary = "## 全球新闻总结\n\n"
    
    for i, news in enumerate(news_summary, 1):
        formatted_summary += f"{i}. **{news['title']}**\n"
        formatted_summary += f"   - 发布时间：{news['pub_date']}\n"
        formatted_summary += f"   - 链接：{news['link']}\n\n"
    
    return formatted_summary

if __name__ == "__main__":
    try:
        news_summary = get_news_summary()
        formatted_summary = format_news_summary(news_summary)
        print(formatted_summary)
    except Exception as e:
        print(f"获取新闻失败：{e}")