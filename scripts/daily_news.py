
#!/usr/bin/env python3
import subprocess
import requests
from bs4 import BeautifulSoup

def get_news():
    """获取今日热点新闻"""
    try:
        url = "https://top.baidu.com/board?tab=realtime"
        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
        }
        response = requests.get(url, headers=headers, timeout=5)
        response.raise_for_status()
        # 设置正确的编码，防止中文乱码
        response.encoding = response.apparent_encoding
        soup = BeautifulSoup(response.text, "html.parser")
        news_list = []
        # 找到新闻标题的标签
        for item in soup.find_all("div", class_="c-single-text-ellipsis", limit=5):
            title = item.text.strip()
            if title:
                news_list.append(f"- {title}")
        return '\n'.join(news_list)
    except Exception as e:
        print(f"获取新闻失败: {e}")
        return "未能获取到今日热点新闻"

def send_message(content):
    """发送消息给用户"""
    try:
        cmd = f"clawdbot message send --channel feishu --target ou_f62763f9be04a1580329d7b8f77041a4 --message '{content}'"
        result = subprocess.run(cmd, shell=True, capture_output=True, text=True, timeout=10)
        print("消息发送成功")
        print(result.stdout)
        if result.stderr:
            print("错误信息:")
            print(result.stderr)
    except Exception as e:
        print(f"发送消息失败: {e}")

def main():
    # 获取新闻
    news = get_news()
    
    # 构建消息
    message = f"今日热点新闻：\n{news}"
    
    # 发送消息
    send_message(message)

if __name__ == "__main__":
    main()
