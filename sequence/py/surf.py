import cv2
import os

print(cv2.__version__)

def read_img(path):
    """Given a path to an image file, returns a cv2 array

    str -> np.ndarray"""
    if os.path.isfile(path):
        return cv2.imread(path)
    else:
        raise ValueError('Path provided is not a valid file: {}'.format(path))

img1 = read_img("/home/trimo/Dev/Git/Hadoop-Image-Matching/sequence/py/25_64.png")

gray = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)

surf = cv2.xfeatures2d.SURF_create()

(kps, descs) = surf.detectAndCompute(gray, None)

print("# kps: {}, descriptors: {}".format(len(kps), descs.shape))