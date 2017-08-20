package com.solvo.hoam.view.detail;

import com.solvo.hoam.domain.model.AdEntity;

public interface DetailViewState {

    final class LoadingState implements DetailViewState {

        @Override
        public String toString() {
            return "LoadingState{}";
        }
    }

    final class ErrorState implements DetailViewState {
        private final Throwable error;

        public ErrorState(Throwable error) {
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

    final class DataState implements DetailViewState {
        private final AdEntity adEntity;

        public DataState(AdEntity adEntity) {
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
