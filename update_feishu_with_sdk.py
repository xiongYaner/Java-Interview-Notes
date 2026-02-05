#!/usr/bin/env python3
"""
使用飞书SDK更新文档内容
"""

from feishu_sdk import FeishuAPI
import os

# 初始化SDK
app_id = "cli_a9f6c35c42b91cbb"
app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"

api = FeishuAPI(app_id, app_secret)

# 获取访问token
try:
    token = api.get_tenant_access_token()
    print("✅ 成功获取访问token")
except Exception as e:
    print(f"❌ 获取访问token失败: {e}")
    os._exit(1)

# 读取文档内容
doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_token = doc_url.split("/wiki/")[1].split("?")[0]

print(f"文档token: {doc_token}")

# 读取要更新的内容
with open("java-interview-materials.md", "r", encoding="utf-8") as f:
    content_str = f.read()

# 尝试更新文档
try:
    print("=== 尝试使用SDK更新文档 ===")
    
    # 获取文档信息
    response = api.docx.get_document(doc_token)
    print(f"文档信息获取成功: {response}")
    
    # 获取文档内容
    content_response = api.docx.get_raw_content(doc_token)
    print(f"当前内容长度: {len(content_response['data']['content'])}")
    
    # 更新文档内容
    update_response = api.docx.update_raw_content(doc_token, content_str)
    print(f"内容更新成功: {update_response}")
    
    # 验证更新
    verify_response = api.docx.get_raw_content(doc_token)
    print(f"更新后内容长度: {len(verify_response['data']['content'])}")
    
    print("✅ 文档更新成功！")
    
except Exception as e:
    print(f"❌ 更新文档失败: {e}")
    import traceback
    print(f"详细错误信息: {traceback.format_exc()}")