package org.example.statemachine;

@FunctionalInterface
public interface TransitionAction<TEvent, TEventContext extends EventContext, TStatus> {
    void execute(TEventContext context, TEvent event, StateMachineContext<TStatus, TEvent> stateContext);
}
