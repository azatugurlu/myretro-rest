package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.BoardRow;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.VoteUser;

@Component
public class BoardRowValidator {
	
	public void validateInsert(BoardRow boardRow, Response response) {
		
		if (boardRow.getBoard_id() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0064.name(), Error.ERR0064.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (boardRow.getColumn_id() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0065.name(), Error.ERR0065.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (boardRow.getAuthorName() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0063.name(), Error.ERR0063.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(BoardValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public void validateInsertRowVote(VoteUser voteUser, Response response) {
		if (voteUser.getUser_id() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (voteUser.getBoard_row_id() == null) {
			response.message(BoardValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (voteUser.getAuthorName().isEmpty()) {
			response.message(BoardValidator.class.getName(), Error.ERR0017.name(), Error.ERR0017.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
