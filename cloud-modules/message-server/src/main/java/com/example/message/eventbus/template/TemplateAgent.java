package com.example.message.eventbus.template;

import com.example.message.eventbus.common.event.ReceiveEvent;
import com.example.message.eventbus.data.TemplateInfo;
import com.example.message.eventbus.template.event.ChangeEvent;
import com.example.message.eventbus.template.event.StateBStartEvent;
import com.example.message.eventbus.template.event.StateBStopEvent;
import com.example.message.eventbus.template.event.StateCStartEvent;
import com.example.message.eventbus.core.BaseEventAgent;
import com.example.message.eventbus.core.EventCenter;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.delegates.Trace;
import com.github.oxo42.stateless4j.transitions.Transition;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * 观察者（Observer）：注册到"被观察者"中，接收通知，执行自己的业务
 */
@Slf4j
public class TemplateAgent extends BaseEventAgent {

    /**
     * 定义状态A,B,C每个状态标识一个特定的行为或状态
     */
    private enum State {
        STATE_A,
        STATE_B,
        STATE_C
        // ...
    }

    /**
     * 定义状态转换条件,开始和停止
     */
    private enum Trigger {
        EVENT_B_START,
        EVENT_B_STOP,
        EVENT_C_START,
        EVENT_C_STOP
    }

    private final TemplateInfo templateInfo;

    // 定义状态机
    private final StateMachine<TemplateAgent.State, TemplateAgent.Trigger> stateMachine;

    public TemplateAgent(EventCenter eventCenter, TemplateInfo templateInfo) {
        super(eventCenter);
        this.stateMachine = createStateMachine();
        this.templateInfo = templateInfo;
    }

    /**
     * 当状态机进入"B"状态时,执行onEntryB
     * @param transition
     */
    private void onEntryB(Transition<TemplateAgent.State, TemplateAgent.Trigger> transition) {
        if (transition.getSource() == TemplateAgent.State.STATE_A) {
            postEvent(new StateBStartEvent(templateInfo.getTemplateId()));
        }
    }

    /**
     *
     * @param transition
     */
    private void onStopB(Transition<TemplateAgent.State, TemplateAgent.Trigger> transition) {
        if (transition.getDestination() == TemplateAgent.State.STATE_A) {
            postEvent(new StateBStopEvent(templateInfo.getTemplateId()));
        }
    }

    /**
     *
     */
    private void onEntryC() {
        postEvent(new StateCStartEvent(templateInfo.getTemplateId()));
    }

    /**
     *
     */
    private void onExitC() {
        postEvent(new StateCStartEvent(templateInfo.getTemplateId()));
    }

    /**
     * 构建状态机
     *
     * @return StateMachine
     */
    private StateMachine<TemplateAgent.State, TemplateAgent.Trigger> createStateMachine() {
        // 创建状态机配置并设置最初状态A
        StateMachine<TemplateAgent.State, TemplateAgent.Trigger> stateMachine = new StateMachine<>(TemplateAgent.State.STATE_A);

        // 定义规则
        stateMachine.configure(TemplateAgent.State.STATE_A)
                // 状态A -> B启动 -> 状态B
                .permit(TemplateAgent.Trigger.EVENT_B_START, TemplateAgent.State.STATE_B);

        stateMachine.configure(TemplateAgent.State.STATE_B)
                // 当状态机进入"B"状态时,执行onEntryB
                .onEntry(this::onEntryB)
                // 当状态机离开"B"状态时,执行onStopB
                .onExit(this::onStopB)
                // 许可转换: 状态B-> C启动 -> 状态C
                .permit(TemplateAgent.Trigger.EVENT_C_START, TemplateAgent.State.STATE_C)
                // 许可转换: 状态B -> B停止 -> 状态A
                .permit(TemplateAgent.Trigger.EVENT_B_STOP, TemplateAgent.State.STATE_A);
        ;

        stateMachine.configure(TemplateAgent.State.STATE_C)
                // 当状态机进入"C"状态时,执行onEntryC
                .onEntry(this::onEntryC)
                // 当状态机离开"C"状态时,执行onStopC
                .onExit(this::onExitC)
                // 许可转换: 状态C -> C停止 -> 状态B
                .permit(TemplateAgent.Trigger.EVENT_C_STOP, TemplateAgent.State.STATE_B);

        // logger
        stateMachine.setTrace(new Trace<TemplateAgent.State, TemplateAgent.Trigger>() {
            @Override
            public void trigger(TemplateAgent.Trigger trigger) {
            }

            @Override
            public void transition(TemplateAgent.Trigger trigger, TemplateAgent.State source, TemplateAgent.State destination) {
                if (log.isDebugEnabled()) {
                    log.debug("Transformer({}): {} -> {} - {}", templateInfo.getTemplateId(), source, destination, trigger);
                }
            }
        });

        return stateMachine;
    }

    // 触发
    private void triggerChange() {
        switch (stateMachine.getState()) {
            case STATE_A:
                // 处理状态机A的逻辑
                // ...
                if (true) { // 满足某个条件
                    /* 触发状态转换条件, 状态跳转 */
                    stateMachine.fire(TemplateAgent.Trigger.EVENT_B_START);
                }
                break;
            case STATE_B:
                if (!true) {
                    stateMachine.fire(TemplateAgent.Trigger.EVENT_B_STOP);
                    break;
                }
                if (true) {
                    stateMachine.fire(TemplateAgent.Trigger.EVENT_C_START);
                    break;
                }
                break;
            case STATE_C:
                if (!true) {
                    // ToDo
                    stateMachine.fire(TemplateAgent.Trigger.EVENT_C_STOP);
                    break;
                }
                break;
        }
    }

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param event 接收事件
     */
    @Subscribe
    public void onReceiveHandleEvent(ReceiveEvent event) {
        // Todo
        execute(() -> {
            if (!event.getTemplateId().equals(templateInfo.getTemplateId())) {
                return;
            }
            // 发布事件
            postEvent(new ChangeEvent(event.getTemplateId(), 0.0));

            triggerChange();
        });
    }
}
