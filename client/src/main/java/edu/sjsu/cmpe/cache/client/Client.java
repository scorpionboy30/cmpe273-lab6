package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        char[] value = {'0','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        List<DistributedCacheService> server = new ArrayList<DistributedCacheService>();
        server.add(new DistributedCacheService("http://localhost:3000"));
        server.add(new DistributedCacheService("http://localhost:3001"));
        server.add(new DistributedCacheService("http://localhost:3002"));
        
        for(int putkey=1; putkey<=10; putkey++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(putkey)), server.size());
        	server.get(bucket).put(putkey, Character.toString(value[putkey]));
        	System.out.println("The key value pair " + putkey +"-" + value[putkey]+ " is assigned to server " + bucket);
        }
        for(int getkey=1; getkey<=10; getkey++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), server.size());
        	System.out.println("The key value pair " + getkey +"-" + server.get(bucket).get(getkey)+ " is received to server " + bucket);
        	
        }
        
    }

}