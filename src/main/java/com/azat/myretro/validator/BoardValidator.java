package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Board;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Team;

@Component
public class BoardValidator {
	
	public void validateInsert(Board board, Response response) {
		if (board.getName() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(BoardValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
