"""
https://www.reddit.com/r/dailyprogrammer/comments/8jcffg/20180514_challenge_361_easy_tally_program/
May 21, 2018
"""

import collections
import sys


def count(to_count):

	#sorted by uppercase
	sorted_count = sorted(to_count)
	upper_list = []
	lower_list = []
	for c in sorted_count:
		if c.isupper():
			upper_list.append(c.lower())
		else:
			lower_list.append(c)

	count = collections.Counter(lower_list)
	count.subtract(collections.Counter(upper_list))
	return count


if __name__ == "__main__":

	if len(sys.argv) <= 1:
		print 'Invalid input!'
		quit()
		
	to_count = sys.argv[1]
	print ((count(to_count).most_common())[0:])