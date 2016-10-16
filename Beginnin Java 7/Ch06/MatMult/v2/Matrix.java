class Matrix
{
   private double[][] matrix;
   Matrix(int nrows, int ncols)
   {
      matrix = new double[nrows][ncols];
   }
   int getCols()
   {
      return matrix[0].length;
   }
   int getRows()
   {
      return matrix.length;
   }
   double getValue(int row, int col)
   {
      return matrix[row][col];
   }
   void setValue(int row, int col, double value)
   {
      matrix[row][col] = value;
   }
}
