function creategui()
{
   var swinggui = new JavaImporter(java.awt, javax.swing);
   with (swinggui)
   {
      println("Event-dispatching thread: "+EventQueue.isDispatchThread());
      var r = new java.lang.Runnable()
              {
                 run: function()
                 {
                    println("Event-dispatching thread: "+
                            EventQueue.isDispatchThread());
                    var frame = new JFrame("Swing GUI");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    var label = new JLabel("Hello from JavaScript",
                                           JLabel.CENTER);
                    label.setPreferredSize(new Dimension(300, 200));
                    frame.add(label);
                    frame.pack();
                    frame.setVisible(true);
                 }
              };
      EventQueue.invokeLater(r);
   }
}
