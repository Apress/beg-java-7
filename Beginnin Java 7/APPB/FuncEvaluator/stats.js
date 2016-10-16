function combinations(n, r)
{
   return fact(n)/(fact(r)*fact(n-r))
}
function fact(n)
{
   if (n == 0)
      return 1;
   else
      return n*fact(n-1);
}
