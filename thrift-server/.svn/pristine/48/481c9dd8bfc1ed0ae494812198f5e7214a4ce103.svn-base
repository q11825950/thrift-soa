package com.example.service;

import com.github.mql.rpc.provider.RpcService;
import com.github.thrift.service1.user.UserDTO;
import com.github.thrift.service1.user.UserQuery;
import com.github.thrift.service1.user.UserService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcService
public class UserServiceImpl implements UserService.Iface
{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public UserDTO getById(long id) throws TException
	{
		logger.info("getById "+id);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setName("longxiangfu");
		return userDTO;
	}

	@Override
	public List<UserDTO> search(UserQuery query) throws TException
	{
		logger.info("search ");
		return null;
	}

}
