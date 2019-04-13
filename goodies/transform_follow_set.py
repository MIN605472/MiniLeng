#!/usr/bin/env python3

import sys

follow_set_fn = sys.argv[1]
follow_set_dict = {}

with open(follow_set_fn, 'r') as follow_set_file:
    for line in follow_set_file.readlines():
        line = line.replace("'", '_').replace('$','EOF')
        h = line.split('-->')
        follow_set_dict[h[0].strip()] = h[1].split()

for key, tokens in follow_set_dict.items():
    print('{')
    print('  Set<String> s = new HashSet<>();')
    for t in tokens:
        print('  s.add("{}");'.format(t))
    print('  followSets.put("{}", s);'.format(key))
    print('}')
