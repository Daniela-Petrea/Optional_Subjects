from Crypto.Util import number
import sympy
import math
import random
from sympy import jacobi_symbol
#I tried to make the correct setup
#p and q are two prime numbers on 1024 bits and p%4==3 and q%4==3
# using pycryptodome to generate a prime number on 1024 bits
p=number.getPrime(1024)
while(p%4!=3):
    p=number.getPrime(1024)
q=number.getPrime(1024)
while(q%4!=3):
    q=number.getPrime(1024)
n=p*q
s=random.randint(1,1024000)
while(math.gcd(n,s)!=1):
    s=random.randint(1,1024)
#BBM generator
x=[]
b=[]
compression=''
x.append((s**2)%n)
b.append(((s**2)%n)%2)
compression+=str(b[0])
i=random.randint(100,150)
nr_zero=0
nr_one=0
for index in range(1,i):
    nr=(x[index-1]**2)%n
    x.append(nr)
    nrb=x[index]%2
    if(nrb==0):
        nr_zero=nr_zero+1
        compression+='0'
    else:
        nr_one=nr_one+1
        compression+='1'
    b.append(nrb)
print(f'number of 0: {nr_zero} \nnumber of 1: {nr_one}')
print(f'bbs: {compression}')
#function to make compression using Lempel-Ziv's algorithm
def compress(string):
    number_of_0_or_1 = {'0': 0, '1': 1}
    word = ""
    result = []
    for c in string:
        wc = word + c
        if wc in number_of_0_or_1:
            word = wc
        else:
            result.append(number_of_0_or_1[word])
            number_of_0_or_1[wc] = len(number_of_0_or_1)
            word = c
    if word:
        result.append(number_of_0_or_1[word])
        return result
t1 = compress(compression)
sequence_of_1=''
for index_for_compression in range(1,len(compression)):
    sequence_of_1+='1'
t2 = compress(sequence_of_1)
print(t1)
print(t2)
print(f'compression is {len(t1)/len(t2)}')
#Jacobi generator
#function that uses Gauss's criterion to verify
def gauss(a, nr):
    assert(nr>2  and a%nr!=0)
    i1=nr//2//a+1
    i2=nr//a
    r=i2-i1+1
    return (-1)**r
#function that implements the third, fourth and fifth rules
def primary_rules(a,nr):
    assert (nr > 2 )
    if(a==1):
        return 1
    if(a==-1):
        if(nr%4==1):
            return 1
        else:
            if(nr%4==3):
                 return -1
    if(a==2):
        if(nr%8==1 or nr%8==7):
            return 1
        else:
            if(nr%8==3 or nr%8==5):
                return -1
    return -10
#implements the first, second and sixth rules
def jacobi_symbol_rule(a,nr):
    semn=1
    while(primary_rules(a,nr)==-10):
        if (sympy.isprime(a)==True):
            if (a % 4 == 1 or nr % 4 == 1):
                if(nr%2==1):
                    a, nr = nr, a
            elif (a % 4 == 3 and nr % 4 == 3):
                a, nr = nr, a
                semn=semn*(-1)
        else:
            for indexagain in range (2, int(math.sqrt(a))+1):
                if(a%indexagain==0):
                    semn=semn*jacobi_symbol(indexagain,nr)
                    a=a//indexagain
        if(a>nr):
            a = a % nr
    return semn*primary_rules(a,nr)
j=[]
nr_minus_one=0
number_one=0
compression1=''
for index1 in range(0,i-1):
    numbern=s+index1
    result = jacobi_symbol_rule(numbern,p)*jacobi_symbol_rule(numbern,q)
    assert result==jacobi_symbol(numbern,n)
    j.append(result)
    if(result==1):
        number_one = number_one + 1
        compression1 += '1'
    else:
        nr_minus_one = nr_minus_one + 1
        compression1 += '0'
print(compression1)
print(f'number of -1: {nr_minus_one} \nnumber of 1: {number_one}')
print(f'jacobi generator: {compression1}')
t3 = compress(compression1)
print(t3)
print(t2)
print(f'compression is {len(t3)/len(t2)}')