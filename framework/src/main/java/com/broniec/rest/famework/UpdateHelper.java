package com.broniec.rest.famework;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class UpdateHelper {

     public <T extends Entity<T, ?>> void updateCollection(Collection<T> collection, Collection<T> referenceCollection) {
         var collectionById = collection.stream().collect(Collectors.groupingBy(Entity::getId));
         var refCollectionById = referenceCollection.stream().collect(Collectors.groupingBy(ref -> isNull(ref.getId()) ? "no-key" : ref.getId()));

         collection.removeIf(entity -> !refCollectionById.containsKey(entity.getId()));

         referenceCollection.forEach(refEntity -> {
             var matchingById = Objects.optionalCollection(() -> collectionById.get(refEntity.getId()));
             matchingById.forEach(entity -> entity.update(refEntity));
             if (matchingById.isEmpty()) {
                 collection.add(refEntity);
             }
         });
     }

}
