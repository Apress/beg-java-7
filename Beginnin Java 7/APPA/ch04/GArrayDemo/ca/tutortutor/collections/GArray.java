package ca.tutortutor.collections;

public class GArray<E>
{
   private E[] array;
   private int size;
   @SuppressWarnings("unchecked")
   public GArray(int initCapacity)
   {
      array = (E[]) new Object[initCapacity];
      size = 0;
   }
   public E get(int index)
   {
      if (index < 0 || index >= size)
         throw new ArrayIndexOutOfBoundsException(index+" out of bounds");
      return array[index];
   }
   public void set(int index, E value)
   {
      if (index < 0)
         throw new ArrayIndexOutOfBoundsException(index+" out of bounds");
      if (index >= array.length)
      {
         @SuppressWarnings("unchecked")
         E[] array2 = (E[]) new Object[index*2];
         System.arraycopy(array, 0, array2, 0, size);
         array = array2;
      }
      array[index] = value;
      if (index >= size)
         size = index+1;
   }
   public int size()
   {
      return size;
   }
}
