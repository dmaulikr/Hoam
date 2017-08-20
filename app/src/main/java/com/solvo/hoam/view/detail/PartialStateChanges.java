package com.solvo.hoam.view.detail;

import com.solvo.hoam.domain.model.AdEntity;

public interface PartialStateChanges {

    final class Loading implements PartialStateChanges {

        @Override
        public String toString() {
            return "LoadingState{}";
        }
    }

    final class Error implements PartialStateChanges {
        private final Throwable error;

        public Error(Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }

        @Override
        public String toString() {
            return "ErrorState{" +
                    "error=" + error +
                    '}';
        }
    }

    final class Data implements PartialStateChanges {
        private final AdEntity adEntity;

        public Data(AdEntity adEntity) {
            this.adEntity = adEntity;
        }

        public AdEntity getAdEntity() {
            return adEntity;
        }

        @Override
        public String toString() {
            return "DataState{" +
                    "adEntity=" + adEntity +
                    '}';
        }
    }
}
