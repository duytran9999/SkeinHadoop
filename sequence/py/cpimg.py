#!/usr/bin/python

import sys
from PIL import Image
from functools import reduce

def avhash(im):
    if not isinstance(im, Image.Image):
        im = Image.open(im)

    im = im.resize((8, 8), Image.ANTIALIAS).convert('L')
    #reduce(lambda x,y:{print(x),print(y)}, im.getdata())
    # c = 0;
    # for i in im.getdata():
    #     print(i)
    #     c=c+1
    #print(c)

    print(list(im.getdata()))
    
    print("=========")
    avg = reduce(lambda x, y: x + y, im.getdata()) / (64.0)
    #lambda  1->n arg
    #reduce 2 arg
    return reduce(lambda x, tup:x|tup[1]<<tup[0],enumerate(map(lambda i: 0 if i < avg else 1, im.getdata())),0)

def hamming(h1, h2):
    h, d = 0, h1 ^ h2
    while d:
        h += 1
        d &= d - 1
    return h

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print ("Usage: %s img1 img2" % sys.argv[0])
    else:
        img1 = sys.argv[1]
        img2 = sys.argv[2]
        hash1 = avhash(img1)
        hash2 = avhash(img2)
        dist = hamming(hash1, hash2)
        print ("""hash(%s) = %d\n
            hash(%s) = %d\n
            hamming distance = %d\n
            similarity = %d%%""" % 
            (img1, hash1, img2, hash2, dist, (64 - dist) * 100 / (64)))


# http://phash.org/docs/
# http://www.hackerfactor.com/blog/index.php?/archives/432-Looks-Like-It.html