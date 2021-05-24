package com.lsatin.topclass.web.basic.enums;

public enum RestfulResponse implements Response {
    SUCCESS {
        @Override
        public int getCode() {
            return 200;
        }

        @Override
        public String getMessage() {
            return "success";
        }
    }, FAIL {
        @Override
        public int getCode() {
            return 500;
        }

        @Override
        public String getMessage() {
            return "fail";
        }
    }, NORMAL {
        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMessage() {
            return "ok";
        }
    }
}
