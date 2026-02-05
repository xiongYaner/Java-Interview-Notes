#!/usr/bin/env python3
import requests
import re

# 直接搜索飞书API文档
keywords = ["docx文档", "文档内容更新", "raw_content"]
search_url = "https://open.feishu.cn/search"

for keyword in keywords:
    print(f"\n=== 搜索: {keyword} ===")
    
    params = {
        "from": "openapi",
        "query": keyword
    }
    
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
        "Connection": "keep-alive"
    }
    
    try:
        response = requests.get(search_url, params=params, headers=headers, timeout=10)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找API文档链接
            doc_links = []
            api_pattern = r'https://open\.feishu\.cn/document/[^\s"\'<>]+'
            matches = re.findall(api_pattern, response.text)
            
            for link in matches[:10]:
                if any(keyword in link.lower() for keyword in ["docx", "document", "update"]):
                    print(f"- {link}")
                    
                    try:
                        # 访问API文档页面
                        doc_response = requests.get(link, headers=headers, timeout=10)
                        
                        # 查找API接口定义
                        if "GET" in doc_response.text or "POST" in doc_response.text:
                            print(f"  ✅ 包含API接口定义")
                            
                            # 查找接口地址
                            url_pattern = r'(?:GET|POST|PUT|PATCH|DELETE)\s+https://open\.feishu\.cn/open-apis/[^"\s]+'
                            url_matches = re.findall(url_pattern, doc_response.text)
                            for api_url in url_matches[:5]:
                                print(f"    {api_url}")
                            
                    except Exception as e:
                        print(f"  ❌ 访问失败: {e}")
                        
        else:
            print(f"搜索失败: {response.status_code}")
            
    except Exception as e:
        print(f"搜索错误: {e}")

print("\n=== 搜索完成 ===")