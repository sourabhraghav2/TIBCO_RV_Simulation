proc getMapRequest {{vfeiMsg {}} {replyBox {}} {step 0}} {
	mbx put TRACE "Inside getMapRequest"
	 if {$step == 0} {
      set replyBox [set ::dmh::mbxreply]
      set vfeiMsg [set ::dmh::mbxmsg]
      after 1 [list eval getMapRequest \{$vfeiMsg\} \{$replyBox\} 1]
      return 0
   }
   global emt_mid_sums emt_lot_sums
   foreach el [vfei_2_array $vfeiMsg msgMap] {set $el $msgMap($el)}
   mbx put TRACE $UPLOAD_MID
   mbx put TRACE $UPLOAD_MAPID 	
   mbx put TRACE $TYPE
   if {$TYPE =="STRIP_MAP"} {
		set type lastQueriedStrip
   } else {
		set type lastQueriedWafer
   }
   
   set cachedMap $emt_mid_sums(${UPLOAD_MID},${type},${UPLOAD_MAPID})   
  if {$replyBox!=""} {
	 set  rplyVar $cachedMap
	 mbx put $replyBox $rplyVar
  }
   
}
