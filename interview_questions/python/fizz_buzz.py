"""
https://blog.codinghorror.com/why-cant-programmers-program/
"""

def fizzBuzz():

	for i in range(1, 101):
		output = str(i)
		if i % 3 == 0:
			output += " fizz"
		if i % 5 == 0:
			output += " buzz"
		print(output)

if __name__ == "__main__":
	fizzBuzz()