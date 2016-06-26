import json
import glob

files = []

for filename in glob.iglob('./**/*.json', recursive=True):
	files.append(filename)

for file in files:
	with open(file) as data_file:
		data = json.load(data_file)

		if 'display' in data:
			del data["display"]

	with open(file, 'w') as data_file:
		json.dump(data, data_file, indent=4, separators=(',', ': '))

	print(file)
