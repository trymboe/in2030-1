def hei(a):
  print(a)
  if a!= 0:
	return hei(a-1)
hei(10)

def pow (a, b):
  if b > 1:
	print("if")
    return pow(a,b-1) * a
  elif b < 1:
  	print("elif")
    return pow(a,b+1) / a
  else:
  	print("else")
    return a

print(pow(10, 2))
