
import os
import imageio

nbr_img = 1257
png_dir = 'img/'
images = []


print("Begin to all the images")
for i in range(1,nbr_img+1):
	path = 'img/'+str(i)+'.png'
	print(path)
	images.append(imageio.imread(path))
print("All image are load")

print("Begin to render the gif")
imageio.mimsave('img/movie.gif', images, fps=30)
print("Gif successfully render ! o/")