package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * ユーザの実行した修正情報
 */
public record EditLineActions(List<EditLineAction> actions) {

  public EditLineActions() { this(new ArrayList<>()) ;}

  public EditLineActions extractActions(Class<? extends EditLineAction> key) {

    return actions.stream().reduce(
        new EditLineActions(),
        (now, nextCommand) -> {

          var compared = nextCommand.getClass();
          if (compared != key) return now;

          return now.add(nextCommand);
        },
        EditLineActions::addAlls
    );
  }

  private EditLineActions add(EditLineAction nextCommand) {
    if (nextCommand == null) return this;

    actions.add(nextCommand);
    return new EditLineActions(actions);
  }

  public EditLineActions addAlls(EditLineActions addCommands) {

    return addCommands
        .actions.stream()
        .reduce(
            this,
            EditLineActions::add,
            EditLineActions::addAlls
        );
  }

  public void forEach(Consumer<EditLineAction> action) {
    actions.forEach(action);
  }
}
