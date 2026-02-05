
#!/bin/bash

# 获取今日热点新闻
NEWS=$(curl -s "https://api.example.com/news" | grep -o '[""]title[""]:[""][^""]*' | head -5 | sed 's/"title":"//;s/"//')

# 获取南京江宁天气
JIANGNING_WEATHER=$(curl -s "wttr.in/南京江宁?format=%l:+%c+%t+%h+%w")

# 获取泰州天气
TAIZHOU_WEATHER=$(curl -s "wttr.in/泰州?format=%l:+%c+%t+%h+%w")

# 生成消息内容
MESSAGE="今日热点新闻：
$NEWS

天气预报：
$JIANGNING_WEATHER
$TAIZHOU_WEATHER"

# 发送消息
clawdbot message send --channel feishu --target ou_f62763f9be04a1580329d7b8f77041a4 --message "$MESSAGE"
