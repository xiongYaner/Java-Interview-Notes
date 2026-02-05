#!/usr/bin/env python3
"""
尝试使用不同的HTTP方法更新飞书文档内容
"""

import os
import requests
import json

def get_access_token():
    app_id = os.environ.get("FEISHU_APP_ID")
    app_secret = os.environ.get("FEISHU_APP_SECRET")
    
    if not app_id or not app_secret:
        raise Exception("FEISHU_APP_ID 或 FEISHU_APP_SECRET 环境变量未设置")
    
    url = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal"
    headers = {
        "Content-Type": "application/json"
    }
    data = {
        "app_id": app_id,
        "app_secret": app_secret
    }
    
    response = requests.post(url, headers=headers, json=data)
    
    if response.status_code != 200:
        raise Exception(f"获取访问token失败: {response.text}")
    
    result = response.json()
    if result.get("code") != 0:
        raise Exception(f"获取访问token失败: {result.get('msg')}")
    
    return result.get("tenant_access_token")

def try_update(access_token, doc_token, content):
    """尝试多种HTTP方法来更新内容"""
    url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/raw_content"
    headers = {
        "Authorization": f"Bearer {access_token}",
        "Content-Type": "application/json"
    }
    
    data = {
        "content": content
    }
    
    # 尝试各种HTTP方法
    for method in ["PUT", "POST", "PATCH"]:
        print(f"=== Trying {method} ===")
        try:
            if method == "PUT":
                response = requests.put(url, headers=headers, json=data)
            elif method == "POST":
                response = requests.post(url, headers=headers, json=data)
            elif method == "PATCH":
                response = requests.patch(url, headers=headers, json=data)
            
            print(f"Status: {response.status_code}")
            print(f"Response: {response.text}")
            
            if response.status_code == 200:
                result = response.json()
                if result.get("code") == 0:
                    print("✅ Success!")
                    return result
                    
        except Exception as e:
            print(f"Error: {e}")
    
    return None

def get_raw_content(access_token, doc_token):
    """获取当前文档内容"""
    url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/raw_content"
    headers = {
        "Authorization": f"Bearer {access_token}"
    }
    
    response = requests.get(url, headers=headers)
    
    if response.status_code == 200:
        result = response.json()
        if result.get("code") == 0:
            return result.get("data", {}).get("content")
    
    return None

def main():
    try:
        access_token = get_access_token()
        print(f"Access token obtained successfully")
        
        doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
        doc_token = doc_url.split("/wiki/")[1].split("?")[0]
        print(f"Document token: {doc_token}")
        
        # 获取当前内容
        current_content = get_raw_content(access_token, doc_token)
        if current_content:
            print(f"Current content length: {len(current_content)} characters")
            print(f"Current content: {repr(current_content)}")
        
        # 读取要更新的内容
        with open("java-interview-materials.md", "r", encoding="utf-8") as f:
            new_content = f.read()
        
        print(f"\nNew content length: {len(new_content)} characters")
        print(f"Content snippet: {repr(new_content[:100])}")
        
        # 尝试更新
        print("\n=== Attempting to update content ===")
        result = try_update(access_token, doc_token, new_content)
        
        if result:
            print("\n=== Update successful ===")
            print(json.dumps(result, indent=2, ensure_ascii=False))
            
            # 验证更新
            updated_content = get_raw_content(access_token, doc_token)
            if updated_content:
                print(f"\n=== Updated content ===")
                print(f"Length: {len(updated_content)} characters")
                print(f"Snippet: {repr(updated_content[:100])}")
        else:
            print("\n=== All methods failed ===")
            print("Please check API documentation or permissions")
        
        return 0
        
    except Exception as e:
        print(f"\nError: {e}")
        import traceback
        print(f"Detailed error: {traceback.format_exc()}")
        return 1

if __name__ == "__main__":
    exit_code = main()
    os._exit(exit_code)