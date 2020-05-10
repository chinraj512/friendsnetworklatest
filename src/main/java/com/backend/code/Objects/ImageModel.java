package com.backend.code.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="ImageModel")
public class ImageModel {
         @Id
         @GeneratedValue(strategy = GenerationType.IDENTITY)
         private int picId;
         private String name;
         private String type;
         private byte[] picByte;
         public ImageModel(String name, String type, byte[] picByte) {
             this.name=name;
             this.type=type;
             this.picByte=picByte;
        }
        public void setPicId(int picId)
        {
            this.picId=picId;
        }
        public int getPicId()
        {
            return picId;
        }
		public void setName(String name)
         {
             this.name=name;
         }
         public String getName()
         {
             return name;
         }
         public void setType(String type)
         {
             this.type=type;
         }
         public String getType()
         {
             return type;
         }
         public void setPicByte(byte[] picByte)
         {
             this.picByte=picByte;
         }
         public byte[] getPicByte()
         {
             return picByte;
         }
}