package org.example;

import org.example.model.UserImp;
import org.reactivestreams.Publisher;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.listener.ListAddListener;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryUpdatedListener;
import org.redisson.config.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainRedisson {
    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");

        RedissonClient client = Redisson.create(config);

        /*

        RAtomicLong myLong = client.getAtomicLong("id");
        System.out.println(myLong);

        RFuture<Boolean> isSet = myLong.compareAndSetAsync(1, 27);
        // because of async first compare and then set so if we want see the new id, we need wait
        Thread.sleep(1000);

//        boolean isSetPublisher = myLong.compareAndSet(1, 28);

        System.out.println(client.getAtomicLong("id"));
         */

        RBucket<UserImp> bucket = client.getBucket("userimp");
        bucket.set(new UserImp(1, "hojjatRe", "hojjat@gmail.com"));
        boolean a = bucket.trySet(new UserImp(1, "hojjatRe", "hojjat@gmail.com"));
        System.out.println(a);
        UserImp userImp = bucket.get();
        System.out.println(userImp.getUsername() + ", " + userImp.getEmail());

        RTopic topic = client.getTopic("myTopic");

        topic.addListener(UserImp.class, (channel, userImp1) -> {
            System.out.println("Received object: " + userImp1.getEmail() + ", " + userImp1.getUsername());
        });

        // Publish a message to the topic
        topic.publish(new UserImp(2, "Omid", "omid@gmail.com"));

        // Shutdown Redisson client
//        client.shutdown();


        RMap<String, UserImp> map = client.getMap("myMap");
//        Map<String, UserImp> map = new HashMap<>();
        map.put("0 User", new UserImp(1, "Hamed", "hamed@gmail.com"));
        map.put("1 User", new UserImp(2, "Reza", "reza@gmail.com"));
//        boolean fasttemp = map.fastPut("2 User", new UserImp(3, "Ali", "ali@gmail.com"));
        map.put("2 User", new UserImp(3, "Ali", "ali@gmail.com"));
        map.put("3 User", new UserImp(3, "Mohammad", "mohammad@gmail.com"));
//        System.out.println(temp.getUsername());
//        System.out.println(fasttemp);
//        System.out.println(map.get("3 User").getUsername());

        /*
        RLock keyLock = map.getLock("0 User");
        keyLock.lock();
        try {
            UserImp v = map.get("0 User");
            v.setUsername("change name in lock");
            System.out.println("In lock: " + v.getUsername());
        } finally {
            keyLock.unlock();
        }


        RReadWriteLock rwLock = map.getReadWriteLock("0 User");
        rwLock.readLock().lock();
        try {
            UserImp v = map.get("0 User");
            v.setEmail("change@gmail.com");
            System.out.println("In getReadWriteLock: " + v.getEmail());
        } finally {
            rwLock.readLock().unlock();
        }

         */
//        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
//                .storeCacheMiss(true)
//                .cacheProvider(LocalCachedMapOptions.CacheProvider.REDISSON)
//                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LRU)
//                .cacheSize(100)
//                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.LOAD)
//                .syncStrategy(LocalCachedMapOptions.SyncStrategy.NONE)
//                .timeToLive(1, TimeUnit.MINUTES);

        RMapCache<String, UserImp> mapCache = client.getMapCache("userMapCache");
        mapCache.put("key1", new UserImp(10, "Abolfazl", "abolfazl@gmail.com"));
//                1, TimeUnit.MINUTES, 1, TimeUnit.MINUTES);

//        System.out.println(mapCache.get("key1").getUsername());
//
//        RLocalCachedMap<Object, Object> clearMap = client.getLocalCachedMap("newmap",
//                LocalCachedMapOptions.defaults().cacheSize(1).syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE));
//        RLocalCachedMap<Object, Object> loadMap = client.getLocalCachedMap("newmap",
//                LocalCachedMapOptions.defaults().cacheSize(1).syncStrategy(LocalCachedMapOptions.SyncStrategy.NONE));
//
//        loadMap.putAll(map);
//        clearMap.clearLocalCache();
//
//        System.out.println(((UserImp) loadMap.get("0 User")).getUsername());
//        System.out.println(loadMap.size());



        int updateListener = mapCache.addListener(new EntryUpdatedListener<String, UserImp>() {
            @Override
            public void onUpdated(EntryEvent<String, UserImp> entryEvent) {
                entryEvent.getKey();
                entryEvent.getValue();
            }
        });

        map.put("0 User", new UserImp(1, "Hamed listen", "hamedListen@gmail.com"));
        System.out.println(map.get("0 User").getUsername());

        RScoredSortedSet<UserImp> set = client.getScoredSortedSet("userSortedSet");
        set.add(0.1, new UserImp(1, "Hojjat", "hojjat@gmail.com"));
        set.add(0.2, new UserImp(1, "Hamed", "hamed@gmail.com"));
        set.add(0.3, new UserImp(1, "Reza", "reza@gmail.com"));
        System.out.println(set.pollLast().getUsername());

//        RBlockingQueue<String> queue = client.getBlockingQueue("myQueue");



    }

}
