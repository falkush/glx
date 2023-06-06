ffmpeg -framerate 60 -i img%%04d.bmp -c:v libx264 -crf 0 output.mp4
pause