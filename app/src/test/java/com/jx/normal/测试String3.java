package com.jx.normal;

import org.junit.Test;

public class 测试String3
{
   @Test
   public void main() {
      test3("222");
   }

   private void test3(String name){
      name = null;
      System.out.println("name=" + name);
   }
}
