class G2D
{
   private Matrix xform;
   G2D()
   {
      xform = new Matrix(3, 3);
      xform.matrix[0][0] = 1.0;
      xform.matrix[1][1] = 1.0;
      xform.matrix[2][2] = 1.0;
      xform.dump();
   }
   private class Matrix
   {
      private double[][] matrix;
      Matrix(int nrows, int ncols)
      {
         matrix = new double[nrows][ncols];
      }
      void dump()
      {
         for (int row = 0; row < matrix.length; row++)
         {
            for (int col = 0; col < matrix[0].length; col++)
               System.out.print(matrix[row][col]+" ");
            System.out.println();
         }
         System.out.println();
      }
   }
   public static void main(String[] args)
   {
      G2D g2d = new G2D();
   }
}
