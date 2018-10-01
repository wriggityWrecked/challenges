"""
https://www.reddit.com/r/dailyprogrammer/comments/45w6ad/20160216_challenge_254_easy_atbash_cipher/
March 31, 2016
"""

import string
 
alphabetList = list(string.lowercase)

def getAtbashCharacter(inputCharacter):
	
	if inputCharacter in string.ascii_letters:
		
		index = alphabetList.index(inputCharacter.lower())
		charToReturn = alphabetList[-(index+1)]

		if inputCharacter in string.uppercase:
			charToReturn = charToReturn.upper()
		
		return charToReturn

	else:
		return inputCharacter

def getAtbashString(inputString):

	returnString = ''
	for c in inputString:
		returnString += getAtbashCharacter(c)

	return returnString

def main():
	input = raw_input()
	print getAtbashString(input)

if __name__ == '__main__':
	main()