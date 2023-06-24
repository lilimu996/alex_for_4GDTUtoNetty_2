package com.rxkj.protocol;

import com.rxkj.message.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class PersonCodec {

    public static class PersonEncoder extends MessageToByteEncoder<Person> {
        @Override
        //编码，将Person对象编码为字节发送出去
        protected void encode(ChannelHandlerContext ctx, Person person, ByteBuf out) throws Exception {
            byte[] serialized = ObjectSerializer.serialize(person);
            out.writeInt(serialized.length);
            out.writeBytes(serialized);
        }
    }

    public static class PersonDecoder extends ByteToMessageDecoder {
        @Override
        //解码,从客户端接收到的字节还原成Person对象并且发送给下一个handler
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() < 4) {
                return; // Not enough bytes to read the length
            }

            int length = in.readInt();
            if (in.readableBytes() < length) {
                in.resetReaderIndex(); // Reset the reader index to read the length on the next call
                return; // Not enough bytes to read the full object
            }

            byte[] serialized = new byte[length];
            in.readBytes(serialized);

            Object obj = ObjectSerializer.deserialize(serialized);
            out.add(obj);
        }
    }
}

