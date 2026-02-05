#!/usr/bin/env python3
import requests
import re
import time

search_terms = [
    "飞书API docx文档更新接口",
    "Feishu API docx v1 document update",
    "飞书API文档内容更新方法",
    "飞书docx API参数格式"
]

for term in search_terms:
    print(f"\n=== 搜索: {term} ===")
    
    # 使用Bing搜索
    search_url = f"https://www.bing.com/search?q={requests.utils.quote(term)}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        "Accept-Language": "zh-CN,zh;q=0.8",
        "Connection": "keep-alive"
    }
    
    try:
        response = requests.get(search_url, headers=headers, timeout=10)
        
        if response.status_code == 200:
            print("搜索成功")
            
            # 查找飞书开放平台文档链接
            pattern = r'https://open\.feishu\.cn/document/[^"\s]+'
            matches = re.findall(pattern, response.text)
            
            found_docs = False
            for match in matches:
                if any(keyword in match for keyword in ["docx", "document", "update"]):
                    print(f"  找到API文档: {match}")
                    
                    # 访问API文档页面
                    try:
                        doc_response = requests.get(match, headers=headers, timeout=10)
                        
                        if doc_response.status_code == 200:
                            # 查找API路径
                            api_pattern = r'https://open\.feishu\.cn/open-apis/[^"\s]+'
                            api_matches = re.findall(api_pattern, doc_response.text)
                            
                            if api_matches:
                                print(f"    API路径:")
                                for api in api_matches[:5]:
                                    if "docx" in api:
                                        print(f"      - {api}")
                        
                        # 查找参数格式
                        if "请求参数" in doc_response.text:
                            print(f"    找到请求参数说明")
                        
                    except Exception as e:
                        print(f"    访问文档失败: {e}")
                        
                    found_docs = True
                    
            if not found_docs:
                print("  未找到API文档")
                
        else:
            print(f"搜索失败: {response.status_code}")
            
    except Exception as e:
        print(f"搜索错误: {e}")
        
    time.sleep(1)  # 避免请求过快

print("\n=== 搜索完成 ===")