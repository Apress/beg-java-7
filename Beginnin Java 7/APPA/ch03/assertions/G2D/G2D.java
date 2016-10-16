import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

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
      assert isIdentity();
   }
   boolean isIdentity()
   {
      return xform.matrix[0][0] == 1.0 && xform.matrix[0][1] == 0.0 &&
             xform.matrix[0][2] == 0.0 && xform.matrix[1][0] == 0.0 &&
             xform.matrix[1][1] == 1.0 && xform.matrix[1][2] == 0.0 &&
             xform.matrix[2][0] == 0.0 && xform.matrix[2][1] == 0.0 &&
             xform.matrix[2][2] == 1.0;
   }
   void rotate(double angle)
   {
      // Make sure rotation is counter-clockwise.
      angle = -angle;
      Matrix r = new Matrix(3, 3);
      double angRad = toRadians(angle);
      r.matrix[0][0] = cos(angRad);
      r.matrix[1][0] = sin(angRad);
      r.matrix[0][1] = -sin(angRad);
      r.matrix[1][1] = cos(angRad);
      r.matrix[2][2] = 1.0;
      xform = xform.multiply(r);
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
      Matrix multiply(Matrix m)
      {
         Matrix result = new Matrix(matrix.length, matrix[0].length);
         for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < m.matrix[0].length; j++)
               for (int k = 0; k < m.matrix.length; k++)
                  result.matrix[i][j] = result.matrix[i][j]+
                                        matrix[i][k]*m.matrix[k][j];
         return result;
      }
   }
   public static void main(String[] args)
   {
      G2D g2d = new G2D();
      g2d.rotate(45);
   }
}
