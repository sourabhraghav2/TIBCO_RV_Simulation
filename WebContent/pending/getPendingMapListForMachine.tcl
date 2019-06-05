proc getPendingMapListForMachine {{vfeiMsg {}} {replyBox {}} {step 0}} {
	#mbx put TRACE "Inside getPendingMapListForMachine"
	 if {$step == 0} {
      set replyBox [set ::dmh::mbxreply]
      set vfeiMsg [set ::dmh::mbxmsg]
      after 1 [list eval getPendingMapListForMachine \{$vfeiMsg\} \{$replyBox\} 1]
      return 0
   }
	global emt_mid_sums emt_lot_sums
	set finalEquipmentAndTheirMapList {}
	foreach index [array names emt_mid_sums] {
		set contains [string first lastQueried  [lindex [split  $index ,] 1] 0]
		if {$contains >=0} {
			if {[lindex [split  $index ,] 1]=="lastQueriedStrip"} {
				set type STRIP_MAP
				append finalEquipmentAndTheirMapList "[lindex [split  $index ,] 0]:[lindex [split  $index ,] 2]:$type,"
			} elseif {[lindex [split  $index ,] 1]=="lastQueriedWafer"}  {
				set type WAFER_MAP
				append finalEquipmentAndTheirMapList "[lindex [split  $index ,] 0]:[lindex [split  $index ,] 2]:$type,"
			}
		}
	}
	set finalEquipmentAndTheirMapList [string range $finalEquipmentAndTheirMapList 0 [expr [string length $finalEquipmentAndTheirMapList]-2]]
	mbx put TRACE $finalEquipmentAndTheirMapList
	mbx put $replyBox $finalEquipmentAndTheirMapList
	#mbx put TRACE "Exist :getPendingMapListForMachine"
}