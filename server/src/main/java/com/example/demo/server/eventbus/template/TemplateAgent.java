package com.example.demo.server.eventbus.event;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.delegates.Trace;
import com.github.oxo42.stateless4j.transitions.Transition;
import com.google.common.eventbus.Subscribe;
import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import com.example.demo.server.eventbus.data.Variable;
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

    // 定义状态机
    private final StateMachine<TemplateAgent.State, TemplateAgent.Trigger> stateMachine;

    public TemplateAgent(EventCenter eventCenter) {
        super(eventCenter);
        this.stateMachine = createStateMachine();
    }

    private void onEntryB(Transition<TemplateAgent.State, TemplateAgent.Trigger> transition) {
        if (transition.getSource() == TemplateAgent.State.STATE_A) {
            //postEvent(new TransformerOilTemperatureStartWarningEvent(transformerInfo.getTransformerId()));
        }
        //checkHistoryInWarning();
    }

    private void onStopB(Transition<TemplateAgent.State, TemplateAgent.Trigger> transition) {
        if (transition.getDestination() == TemplateAgent.State.STATE_A) {
            //postEvent(new TransformerOilTemperatureStopWarningEvent(transformerInfo.getTransformerId()));
        }
        //if (temperatureTimer.isRunning()) {
        //temperatureTimer.stop();
        //}
    }

    private void onEntryC() {
        //postEvent(new TransformerOilTemperatureStartAlarmEvent(transformerInfo.getTransformerId()));
    }

    private void onExitC() {
        //postEvent(new TransformerOilTemperatureStopAlarmEvent(transformerInfo.getTransformerId()));
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
                    //log.debug("Transformer({}): {} -> {} - {}", transformerInfo.getTransformerId(), source, destination, trigger);
                }
            }
        });

        return stateMachine;
    }

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param variable 变量
     */
    @Subscribe
    public void handleEvent(Variable variable) {
        // Todo
        execute(() -> {
        });
    }
}
