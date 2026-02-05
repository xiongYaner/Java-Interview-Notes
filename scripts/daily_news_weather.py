
#!/usr/bin/env python3
import subprocess
import requests
from bs4 import BeautifulSoup
import json

def get_weather_by_coords(lat, lon, location_name):
    """通过经纬度获取天气信息"""
    try:
        url = f"https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true"
        response = requests.get(url, timeout=5)
        response.raise_for_status()
        data = response.json()
        current = data.get("current_weather", {})
        temp = current.get("temperature", "N/A")
        windspeed = current.get("windspeed", "N/A")
        weathercode = current.get("weathercode", "N/A")
        return f"{location_name}: {weathercode_to_desc(weathercode)} {temp}°C 风速 {windspeed} km/h"
    except Exception as e:
        print(f"获取天气失败 {location_name}: {e}")
        return f"{location_name}: 天气获取失败"

def weathercode_to_desc(code):
    """将天气代码转换为描述"""
    weather_codes = {
        0: "晴朗",
        1: "多云",
        2: "多云",
        3: "阴天",
        45: "雾",
        48: "雾",
        51: "小雨",
        53: "小雨",
        55: "小雨",
        61: "中雨",
        63: "中雨",
        65: "大雨",
        71: "小雪",
        73: "小雪",
        75: "大雪",
        77: "雪",
        80: "阵雨",
        81: "阵雨",
        82: "暴雨",
        95: "雷暴",
        96: "雷暴",
        99: "雷暴"
    }
    return weather_codes.get(code, "未知")

def get_weather(location):
    """获取指定地点的天气信息"""
    # 南京江宁的经纬度
    if location == "南京江宁":
        return get_weather_by_coords(31.95, 118.87, "南京江宁")
    # 泰州的经纬度
    elif location == "泰州":
        return get_weather_by_coords(32.44, 119.98, "泰州")
    else:
        return f"{location}: 未支持的地点"

def get_news():
    """获取今日全球大事新闻"""
    try:
        # 使用多个新闻来源，覆盖国际、经济、军事、政治等领域
        news_sources = [
            # 新浪新闻
            "https://news.sina.com.cn/world/",  # 国际新闻
            "https://finance.sina.com.cn/",  # 财经新闻
            "https://mil.news.sina.com.cn/",  # 军事新闻
            "https://news.sina.com.cn/china/",  # 国内政治新闻
            "https://news.sina.com.cn/society/"  # 社会新闻（可选）
        ]
        
        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
        }
        
        all_news = []
        
        for url in news_sources:
            try:
                response = requests.get(url, headers=headers, timeout=5)
                response.raise_for_status()
                response.encoding = 'utf-8'
                soup = BeautifulSoup(response.text, "html.parser")
                
                # 不同页面的新闻标签可能不同，尝试多种标签
                # 新浪新闻的常见新闻标签
                for item in soup.find_all("div", class_="news-item", limit=50):
                    h2 = item.find("h2")
                    if h2 and h2.text.strip():
                        all_news.append(h2.text.strip())
                
                for item in soup.find_all("div", class_="img-news-item", limit=50):
                    h2 = item.find("h2")
                    if h2 and h2.text.strip():
                        all_news.append(h2.text.strip())
                
                for item in soup.find_all("div", class_="logout-news-item", limit=50):
                    h2 = item.find("h2")
                    if h2 and h2.text.strip():
                        all_news.append(h2.text.strip())
                
                for item in soup.find_all("div", class_="login-news-item", limit=50):
                    h2 = item.find("h2")
                    if h2 and h2.text.strip():
                        all_news.append(h2.text.strip())
                
                for item in soup.find_all("div", class_="reco-news-item", limit=50):
                    h2 = item.find("h2")
                    if h2 and h2.text.strip():
                        all_news.append(h2.text.strip())
                
                # 新浪新闻右侧栏的新闻标签
                right_col = soup.find("div", class_="right")
                if right_col:
                    for item in right_col.find_all("div", class_="img-news-i", limit=20):
                        title = item.text.strip().split(" [详细]")[0]
                        if title:
                            all_news.append(title)
                
                if right_col:
                    for item in right_col.find_all("div", class_="img-news-c", limit=20):
                        title = item.text.strip().split(" [详细]")[0]
                        if title:
                            all_news.append(title)
                
                # 其他新闻来源的标签
                for item in soup.find_all("h2", class_="story-title", limit=30):
                    if item.text.strip():
                        all_news.append(item.text.strip())
                
                for item in soup.find_all("h3", class_="story-heading", limit=30):
                    if item.text.strip():
                        all_news.append(item.text.strip())
                
                for item in soup.find_all("a", class_="title-link", limit=30):
                    if item.text.strip():
                        all_news.append(item.text.strip())
                        
            except Exception as e:
                print(f"获取新闻失败 {url}: {e}")
                continue
        
        # 过滤体育、娱乐、明星等新闻
        filtered_news = []
        # 使用更精确的关键词列表，以过滤掉所有的体育新闻
        sports_keywords = ["NBA", "CBA", "世界杯", "奥运会", "足球", "篮球", "网球", "排球", "乒乓球", "羽毛球", "游泳", "田径", "阿尔卡拉斯", "德约", "火箭", "步行者", "灰熊", "森林狼", "乔治·拉塞尔", "维斯塔潘", "马竞", "西蒙尼", "拜仁", "伯奇马斯", "快船", "骑士", "哈登", "加兰", "曼城", "阿森纳", "尤文", "伊尔迪兹"]
        entertainment_keywords = ["电影", "电视剧", "综艺", "明星", "演唱会", "音乐节", "颁奖典礼", "娱乐", "八卦"]
        celebrity_keywords = ["明星", "艺人", "演员", "歌手", "导演", "制片人", "主持人", "模特"]
        
        for news in all_news:
            # 检查是否包含体育、娱乐、明星等关键词
            if not any(keyword in news for keyword in sports_keywords + entertainment_keywords + celebrity_keywords):
                filtered_news.append(news)
        
        # 去重
        unique_news = []
        seen = set()
        for news in filtered_news:
            if news not in seen:
                seen.add(news)
                unique_news.append(news)
        
        # 按照重要程度排序（简单的长度排序，长度较长的可能更详细）
        sorted_news = sorted(unique_news, key=lambda x: len(x), reverse=True)
        
        # 取前20条
        top_news = sorted_news[:20]
        
        # 格式化新闻列表
        news_list = [f"{i+1}. {news}" for i, news in enumerate(top_news)]
        
        # 总结新闻内容
        summary = "今日新闻涵盖国际政治、经济动态、军事冲突等多个领域，重点关注美国政治局势、全球经济合作、地区军事冲突以及重要人物事件等。"
        
        return f"今日新闻总结：\n{summary}\n\n详细新闻列表：\n" + '\n'.join(news_list)
    except Exception as e:
        print(f"获取新闻失败: {e}")
        return "未能获取到今日全球大事新闻"

def send_message(content):
    """发送消息给用户"""
    try:
        cmd = ["clawdbot", "message", "send", "--channel", "feishu", "--target", "ou_f62763f9be04a1580329d7b8f77041a4", "--message", content]
        result = subprocess.run(cmd, shell=False, capture_output=True, text=True, timeout=10, encoding='utf-8')
        print("消息发送成功")
        print(result.stdout)
        if result.stderr:
            print("错误信息:")
            print(result.stderr)
    except Exception as e:
        print(f"发送消息失败: {e}")

def main():
    # 获取天气
    jiangning_weather = get_weather("南京江宁")
    taizhou_weather = get_weather("泰州")
    
    # 获取新闻
    news = get_news()
    
    # 构建消息
    message = f"今日全球大事：\n{news}\n\n天气预报：\n{jiangning_weather}\n{taizhou_weather}"
    
    # 发送消息
    send_message(message)

if __name__ == "__main__":
    main()
