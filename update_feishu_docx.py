#!/usr/bin/env python3
import requests
import json
import os

# 获取访问token
def get_access_token():
    app_id = "cli_a9f6c35c42b91cbb"
    app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"
    
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

# 获取文档信息
def get_document_info(token, doc_id):
    url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}"
    response = requests.get(
        url,
        headers={"Authorization": f"Bearer {token}"}
    )
    
    if response.status_code != 200:
        raise Exception(f"获取文档信息失败: {response.text}")
    
    result = response.json()
    if result.get("code") != 0:
        raise Exception(f"获取文档信息失败: {result.get('msg')}")
    
    return result.get("data")

# 更新文档内容 - 尝试使用正确的API
def update_document_content(token, doc_id, content, title):
    # 读取要更新的内容
    with open(content, "r", encoding="utf-8") as f:
        content_str = f.read()
    
    # 尝试使用PATCH方法更新文档
    url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}"
    payload = {
        "title": title,
        "content": content_str
    }
    
    response = requests.patch(
        url,
        headers={
            "Authorization": f"Bearer {token}",
            "Content-Type": "application/json"
        },
        json=payload
    )
    
    if response.status_code == 200:
        result = response.json()
        if result.get("code") == 0:
            print("✅ 文档更新成功")
            return result
        else:
            raise Exception(f"API错误: {result.get('msg')}")
    else:
        raise Exception(f"HTTP错误: {response.status_code} - {response.text}")

# 测试块级操作API
def update_document_blocks(token, doc_id, content):
    url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks"
    
    # 尝试添加新块
    payload = {
        "requests": [
            {
                "insert_text": {
                    "text": content,
                    "end_index": 0
                }
            }
        ]
    }
    
    response = requests.post(
        url,
        headers={
            "Authorization": f"Bearer {token}",
            "Content-Type": "application/json"
        },
        json=payload
    )
    
    return response

# 主函数
def main():
    try:
        token = get_access_token()
        print(f"访问token获取成功: {token[:20]}...")
        
        doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
        doc_id = doc_url.split("/wiki/")[1].split("?")[0]
        
        print(f"文档ID: {doc_id}")
        
        doc_info = get_document_info(token, doc_id)
        print(f"文档信息: {json.dumps(doc_info, ensure_ascii=False, indent=2)}")
        
        print("=== 尝试更新文档内容 ===")
        
        try:
            result = update_document_content(token, doc_id, "java-interview-materials.md", "Java面试材料（3年工具经验）")
            print(f"更新结果: {json.dumps(result, ensure_ascii=False, indent=2)}")
        except Exception as e:
            print(f"❌ 内容更新失败: {e}")
        
        print("\n=== 尝试块级更新 ===")
        with open("java-interview-materials.md", "r", encoding="utf-8") as f:
            md_content = f.read()
        
        block_response = update_document_blocks(token, doc_id, md_content)
        print(f"块级更新响应: {block_response.status_code} - {block_response.text}")
        
        print("\n=== 验证更新 ===")
        updated_info = get_document_info(token, doc_id)
        print(f"更新后文档: {json.dumps(updated_info, ensure_ascii=False, indent=2)}")
        
        return 0
        
    except Exception as e:
        print(f"❌ 错误: {e}")
        import traceback
        print(f"详细错误: {traceback.format_exc()}")
        return 1

if __name__ == "__main__":
    main()