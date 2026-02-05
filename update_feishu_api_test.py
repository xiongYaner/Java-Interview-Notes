#!/usr/bin/env python3
"""
测试飞书文档API的不同端点
"""

import os
import requests
import json

def get_access_token():
    """获取访问token"""
    app_id = os.environ.get("FEISHU_APP_ID", "cli_a9f6c35c42b91cbb")
    app_secret = os.environ.get("FEISHU_APP_SECRET", "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0")
    
    response = requests.post(
        "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal",
        headers={"Content-Type": "application/json"},
        json={"app_id": app_id, "app_secret": app_secret}
    )
    
    if response.status_code != 200:
        raise Exception(f"获取访问token失败: {response.text}")
    
    result = response.json()
    if result.get("code") != 0:
        raise Exception(f"获取访问token失败: {result.get('msg')}")
    
    return result.get("tenant_access_token")

def test_apis(token, doc_token):
    """测试各种可能的API端点"""
    # 读取要更新的内容
    with open("java-interview-materials.md", "r", encoding="utf-8") as f:
        content = f.read()
    
    test_cases = [
        {
            "name": "docx raw content PUT",
            "url": f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/raw_content",
            "method": "PUT",
            "data": {"content": content}
        },
        {
            "name": "docx content PUT",
            "url": f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/content",
            "method": "PUT",
            "data": {"content": content}
        },
        {
            "name": "docx batch update",
            "url": f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}/batch_update",
            "method": "POST",
            "data": {
                "requests": [
                    {
                        "insert_text": {
                            "text": content,
                            "end_index": 0
                        }
                    }
                ]
            }
        },
        {
            "name": "doc/v2 content",
            "url": f"https://open.feishu.cn/open-apis/doc/v2/content/{doc_token}",
            "method": "POST",
            "data": {"content": content}
        },
        {
            "name": "doc/v1 update",
            "url": f"https://open.feishu.cn/open-apis/doc/v1/update",
            "method": "POST",
            "data": {"token": doc_token, "content": content}
        },
        {
            "name": "wiki/v1 set content",
            "url": f"https://open.feishu.cn/open-apis/wiki/v1/wiki/set_content",
            "method": "POST",
            "data": {"wiki_token": doc_token, "content": content}
        },
        {
            "name": "docx document PUT",
            "url": f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}",
            "method": "PUT",
            "data": {
                "title": "Java面试材料（3年工具经验）",
                "content": content
            }
        },
        {
            "name": "docx document PATCH",
            "url": f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_token}",
            "method": "PATCH",
            "data": {"content": content}
        }
    ]
    
    results = []
    
    for case in test_cases:
        try:
            print(f"=== 测试: {case['name']} ===")
            
            headers = {
                "Authorization": f"Bearer {token}",
                "Content-Type": "application/json"
            }
            
            if case["method"] == "GET":
                response = requests.get(case["url"], headers=headers)
            elif case["method"] == "POST":
                response = requests.post(case["url"], headers=headers, json=case["data"])
            elif case["method"] == "PUT":
                response = requests.put(case["url"], headers=headers, json=case["data"])
            elif case["method"] == "PATCH":
                response = requests.patch(case["url"], headers=headers, json=case["data"])
            
            result = {
                "name": case["name"],
                "method": case["method"],
                "url": case["url"],
                "status_code": response.status_code,
                "response": response.text
            }
            
            print(f"状态码: {response.status_code}")
            print(f"响应: {response.text}")
            
            results.append(result)
            
        except Exception as e:
            result = {
                "name": case["name"],
                "method": case["method"],
                "url": case["url"],
                "status_code": 0,
                "response": str(e)
            }
            print(f"错误: {e}")
            results.append(result)
    
    return results

def main():
    try:
        token = get_access_token()
        print(f"访问token: {token}")
        
        doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
        doc_token = doc_url.split("/wiki/")[1].split("?")[0]
        print(f"文档token: {doc_token}")
        
        print("\n=== 开始测试API ===")
        results = test_apis(token, doc_token)
        
        print("\n=== 测试结果总结 ===")
        for res in results:
            print(f"{res['name']:20} | {res['method']} | {res['status_code']} | {res['response'][:60]}...")
        
        return 0
        
    except Exception as e:
        print(f"错误: {e}")
        import traceback
        print(f"详细信息: {traceback.format_exc()}")
        return 1

if __name__ == "__main__":
    exit_code = main()
    os._exit(exit_code)