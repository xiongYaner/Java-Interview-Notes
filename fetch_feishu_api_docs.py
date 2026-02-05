#!/usr/bin/env python3
"""
尝试使用HTTP请求直接访问飞书API文档
"""

import requests
import re

# 访问飞书API文档的主要页面
url = "https://open.feishu.cn/document/ukTMukTMukTM/reference/docs-v2/document/introduction"

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Accept-Language": "zh-CN,zh;q=0.8",
    "Connection": "keep-alive"
}

try:
    print("=== 访问飞书API文档 ===")
    response = requests.get(url, headers=headers, timeout=10)
    
    if response.status_code == 200:
        print("✅ 访问成功")
        
        # 查找API接口文档链接
        print("\n=== 查找API接口文档链接 ===")
        api_links = re.findall(r'https://open\.feishu\.cn/document/[^"\s]+', response.text)
        
        # 过滤重复链接和非API相关链接
        unique_links = list(set(api_links))
        api_docs = []
        
        for link in unique_links:
            if "reference" in link and any(keyword in link for keyword in ["document", "docx", "blocks"]):
                api_docs.append(link)
        
        print(f"找到 {len(api_docs)} 个API文档链接")
        for link in api_docs[:5]:
            print(f"  - {link}")
            
        # 尝试访问第一个API文档
        if api_docs:
            first_api_url = api_docs[0]
            print(f"\n=== 访问第一个API文档: {first_api_url} ===")
            
            api_response = requests.get(first_api_url, headers=headers, timeout=10)
            
            if api_response.status_code == 200:
                # 查找API路径和参数
                print("✅ API文档访问成功")
                
                # 查找接口地址
                api_endpoints = re.findall(r'https://open\.feishu\.cn/open-apis/[^"\s]+', api_response.text)
                print(f"找到 {len(api_endpoints)} 个API接口")
                
                for endpoint in api_endpoints[:10]:
                    if any(keyword in endpoint for keyword in ["docx", "document", "blocks"]):
                        print(f"  - {endpoint}")
                
                # 查找请求参数
                if "请求参数" in api_response.text:
                    print("\n找到请求参数说明")
                    
                # 查找响应示例
                if "响应示例" in api_response.text:
                    print("找到响应示例")
            else:
                print(f"❌ API文档访问失败: {api_response.status_code}")
                
    else:
        print(f"❌ 访问失败: {response.status_code}")
        
except Exception as e:
    print(f"❌ 错误: {e}")