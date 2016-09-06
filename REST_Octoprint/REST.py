'''Code to get responses from the printer'''
import requests
url='http://0.0.0.0:5000//api/printer/tool?history=true&limit=2'
headers={'x-api-key':'3704E15939FE4DD7BB2F5C6D397C74F1'}
resp = requests.get(url,headers=headers)
print resp.content
