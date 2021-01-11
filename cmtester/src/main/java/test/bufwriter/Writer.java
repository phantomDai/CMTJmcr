package test.bufwriter;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author
 * @version 1.0
 */

public class Writer implements Runnable {
  private int _writeVal;
  private int _writeCount;
  private static Buffer _buf;

  public Writer(Buffer buf,int val) {
    _writeVal = val;
    _writeCount = 0;
    _buf = buf;
  }

  public void run()
  {
    for (int i = 0; i < 10; i++) {
      System.out.println("我在写");
      if (_buf._pos >= _buf._size) continue;
      _buf._array[_buf._pos] = _writeVal;
      synchronized(_buf)
      {
        _buf._count += 1;
      }
    }
  }

  public int getCounter ()
  {
    return _writeCount;
  }
}