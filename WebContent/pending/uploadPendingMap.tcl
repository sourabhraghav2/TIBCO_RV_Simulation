proc uploadPendingMap {{vfeiMsg {}} {replyBox {}} {step 0}} {
	mbx put TRACE "Inside uploadPendingMap"
	 if {$step == 0} {
      set replyBox [set ::dmh::mbxreply]
      set vfeiMsg [set ::dmh::mbxmsg]
      after 1 [list eval uploadPendingMap \{$vfeiMsg\} \{$replyBox\} 1]
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
   set stepName {}
   set convertedMap [recp_b64en A $cachedMap]
   set cmd "CMD/A=\"TDS.MAP_UPDATE.$UPLOAD_MID\" REQ_ID/A=\"MapUpdateRequest\" mapId/A=\"$UPLOAD_MAPID\" mapString/A=\"$convertedMap\" processStep/A=\"$stepName\""
   mbx put TRACE $cmd
	set reply [rm_braces [dmh_do_xact RV_GW $cmd 15000]]
		 mbx put TRACE $reply
		 if {$reply == "TIMEOUT"} {
			set ECD 205
			set ETX "RES_TRANSFER TIMEOUT ON RV_GW"
		 } else {
			mbx put TRACE "----NO TIMEOUT-----"
			vfei_2_array $reply MAP_UPDATE
			foreach el [array names MAP_UPDATE] {
			   set $el $MAP_UPDATE($el)
			   mbx put TRACE "----$el $MAP_UPDATE($el)-----"
			}
			set ECD $errorCode
			set ETX $errorText
		 }
	  
	  if {$replyBox!=""} {
		 
		 set  rplyVar " UPLOAD_MID/A=\"$UPLOAD_MID\"   ECD/U4=\"$ECD\" ETX/A=\"$ETX\""
		 mbx put TRACE "----rplyVar : $rplyVar-----"
		 mbx put $replyBox $rplyVar
			
	  }
   
}
