package com.danram.danram.exception;

import com.danram.danram.exception.comment.CommentIdNotFoundException;
import com.danram.danram.exception.comment.CommentLikeIdNotFoundException;
import com.danram.danram.exception.comment.NotAuthorityException;
import com.danram.danram.exception.feed.FeedIdNotFoundException;
import com.danram.danram.exception.feed.FeedLikeIdNotFoundException;
import com.danram.danram.exception.feed.FeedMakeException;
import com.danram.danram.exception.feed.RoleNotExistException;
import com.danram.danram.exception.member.MemberEmailNotFoundException;
import com.danram.danram.exception.member.MemberIdNotFoundException;
import com.danram.danram.exception.member.MemberLoginTypeNotExistException;
import com.danram.danram.exception.member.MemberNotExistException;
import com.danram.danram.exception.party.*;
import com.danram.danram.exception.s3.FileNameNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(FeedIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FeedIdNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse("DEF-001", "Feed id is not exist: " + ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(RoleNotExistException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse("DEF-002", "This member does not have Role"),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(FeedLikeIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FeedLikeIdNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse("DEF-003", "Member like is not exist" + ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CommentIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CommentIdNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse("DEC-001", "Comment id is not founded: " + ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotAuthorityException.class)
    public ResponseEntity<ApiErrorResponse> handleException(NotAuthorityException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse("DEC-002", "This member has not authority: " + ex.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(CommentLikeIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CommentLikeIdNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse("DEC-003", "Memter did not like this comment: " + ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PartyNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEP-0001","Party is not found - id "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PartyMemberNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyMemberNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEP-0002","party member not found - party or party-member id "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PartyHostExitException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyHostExitException ex) {
        ApiErrorResponse response  = new ApiErrorResponse("DEP-0003","party host cannot exit party - member id "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PartyMemberDuplicatedException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyMemberDuplicatedException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEP-0004","party member duplicated "+ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PartyJoinNotAllowException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyJoinNotAllowException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEP-0005",ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    /**
     * S3 upload exception => DEF(Danram Exception File)
     * */
    @ExceptionHandler(FileNameNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FileNameNotValidException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEF-001" ,"File name is not invalid: ");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * DEM
     * */
    @ExceptionHandler(MemberIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-001", "Member id not found: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberEmailNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberEmailNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-002", "Member email is not found: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberNotExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-003", "There is not member.");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberLoginTypeNotExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberLoginTypeNotExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("DEM-004", "This login type is not exist: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
