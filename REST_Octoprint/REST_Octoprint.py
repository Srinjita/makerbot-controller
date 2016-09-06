#!/usr/bin/env python2.7
import requests
import json

'''Code to get responses from the printer'''
def get_response():
	url='http://0.0.0.0:5000//api/printer/tool?history=true&limit=2' #This include temperature history data, but limit it to two entries. Can be modified to specific needs. 
	headers={'x-api-key':'3704E15939FE4DD7BB2F5C6D397C74F1'} #Modify the api-key from your Octoprint Settings
	resp = requests.get(url, headers=headers)
	print resp.content

'''Code to send beep command'''
def post_request():
    url='http://0.0.0.0:5000//api/printer/command' #Sending commands to printer
    headers={'x-api-key':'3704E15939FE4DD7BB2F5C6D397C74F1', 'Content-type':'application/json'}
    #data = {"command": "M300"} #Sending single command
    data = {"commands": ["M300","M300"]} #Sending multiple commands at a time
    data_json = json.dumps(data)
    response = requests.post(url, data=data_json, headers=headers)
    print response.content #Used to debug code not required otherwise

choice=input('Enter your choice (1 or 2):'+'\n1. To get a reponse from the 3D printer'+'\n2. To send a request to the 3D printer\nYour option:')

if choice==1:
    get_response()
elif choice ==2:
    post_request()
else:
    print ('Choose a correct number to be able to work with the printer')
