package com.springrest.restserver.listenser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.annotation.TopologyChanged;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.notifications.cachelistener.event.TopologyChangedEvent;


@Listener
public class LoggingListener {

   private Log log = LogFactory.getLog(LoggingListener.class);

   @CacheEntryCreated
   public void observeAdd(CacheEntryCreatedEvent<String, String> event) {
      if (event.isPre())
         return;

      log.trace("Cache entry "+event.getKey()+" added in cache "+ event.getCache()+" value "+ event.getValue());
   }

   @CacheEntryModified
   public void observeUpdate(CacheEntryModifiedEvent<String, String> event) {
      if (event.isPre())
         return;

      log.trace("Cache entry "+event.getKey()+" modify in cache "+ event.getCache()+" value "+ event.getValue());
   }

   @CacheEntryRemoved
   public void observeRemove(CacheEntryRemovedEvent<String, String> event) {
      if (event.isPre())
         return;

      log.trace("Cache entry "+event.getKey()+" remoive in cache "+ event.getCache()+" value "+ event.getValue());
   }

   @TopologyChanged
   public void observeTopologyChange(TopologyChangedEvent<String, String> event) {
      if (event.isPre())
         return;

      log.trace("Cache "+event.getCache().getName()+" topology changed, new membership is "+event.getConsistentHashAtEnd().getMembers());
   }
}
