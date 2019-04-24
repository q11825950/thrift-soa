
namespace java com.xxce.service.dbserver

service  SyncRequestService {
	void ping(),
	binary SyncRequest(1: binary reqmsg, 2: i32 type),
	i32 SyncNotice(1: string reqmsg, 2: i32 type)
}

